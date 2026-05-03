package com.josafaverissimo.listfiles.src;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import com.josafaverissimo.listfiles.src.utils.BinarySearchTree;
import com.josafaverissimo.listfiles.src.utils.BinaryTreeNode;
import com.josafaverissimo.listfiles.src.utils.FileComparator;
import com.josafaverissimo.listfiles.src.utils.FileData;

public class ListFiles {
  private BinarySearchTree<FileData> binarySearchTree;
  private Path path;

  public ListFiles(String path) throws IOException {
    this.path = Paths.get(path);
    this.binarySearchTree = new BinarySearchTree<FileData>(new FileComparator());

    this.insertFiles(this.path);
  }

  private void insertFiles(Path path) throws IOException {
    if (!Files.exists(path) || !Files.isDirectory(path))
      throw new IllegalArgumentException(String.format("O path não é um diretório válido: %s", path));

    try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      Files.walk(path)
          .filter(Files::isRegularFile)
          .filter(Files::isReadable)
          .forEach(filePath -> executor.submit(() -> {
            try {
              FileData data = new FileData(filePath.toString(), Files.size(filePath));
              synchronized (this.binarySearchTree) {
                this.binarySearchTree.insert(data);
              }
            } catch (IOException e) {
              // ignore unreadable files
            }
          }));
    } catch (Exception e) {
      System.err.println(String.format("Erro: %s", e.toString()));
    }
  }

  private String humanizeBytes(long bytes) {
    String[] unitMetrics = { "KiB", "MiB", "GiB", "TiB" };

    float readableBytes = bytes;
    String unitMetric = "B";

    for (String unit : unitMetrics) {
      if (readableBytes > 1024) {
        readableBytes /= 1024;
        unitMetric = unit;

        continue;
      }

      break;
    }

    return String.format("%.2f %s", readableBytes, unitMetric);
  }

  public void showFiles() throws IOException {
    AtomicLong total = new AtomicLong(0);

    this.binarySearchTree.inOrderTraverse(file -> total.addAndGet(file.size()), false);

    System.out.println(String.format("Total: %s", humanizeBytes(total.get())));

    FileData biggestFile = this.binarySearchTree.max().key;
    System.out.println(String.format("Biggest file: %s - %s", biggestFile.name(), humanizeBytes(biggestFile.size())));
  }
}
