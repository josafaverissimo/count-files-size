package com.josafaverissimo.listfiles;

import java.io.IOException;

import com.josafaverissimo.listfiles.src.ListFiles;

public class App {
  public static void main(String[] args) {
    try {
      ListFiles lf = new ListFiles(args[0]);

      lf.showFiles();
    } catch (IOException e) {
      System.out.println("Deu erro ó: " + e.toString());
    }
  }
}
