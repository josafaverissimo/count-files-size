package com.josafaverissimo.listfiles.src.utils;

import java.util.Comparator;

public class FileComparator implements Comparator<FileData> {
  @Override
  public int compare(FileData a, FileData b) {
    int sizeOrder = Long.compare(a.size(), b.size());
    if (sizeOrder != 0) return sizeOrder;
    return a.name().compareTo(b.name());
  }
}
