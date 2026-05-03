package com.josafaverissimo.listfiles.src.utils;

public class BinaryTreeNode<T> {
  public T key;
  public BinaryTreeNode<T> left;
  public BinaryTreeNode<T> right;

  public BinaryTreeNode(T key, BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
    this.key = key;
    this.left = left;
    this.right = right;
  }
}
