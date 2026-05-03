package com.josafaverissimo.listfiles.src.utils;

import java.util.Comparator;
import java.util.function.Consumer;

public class BinarySearchTree<T> {
  private BinaryTreeNode<T> root;
  private Comparator<T> nodeComparator;

  public BinarySearchTree(Comparator<T> nodeComparator) {
    this.nodeComparator = nodeComparator;
  }

  public void insert(T key) {
    if (this.root == null) {
      this.root = new BinaryTreeNode<T>(key, null, null);
      return;
    }

    this.insertNode(this.root, key);
  }

  private void insertNode(BinaryTreeNode<T> node, T key) {
    int comparasion = this.nodeComparator.compare(key, node.key);

    if (comparasion == 0)
      return;

    if (comparasion < 0) {
      if (node.left != null) {
        this.insertNode(node.left, key);
        return;
      }

      node.left = new BinaryTreeNode<T>(key, null, null);
      return;
    }

    if (node.right != null) {
      this.insertNode(node.right, key);
      return;
    }

    node.right = new BinaryTreeNode<T>(key, null, null);

  }

  public void inOrderTraverse(Consumer<T> callback, boolean asc) {
    this.inOrderTraverseNode(this.root, callback, asc);
  }

  private void inOrderTraverseNode(BinaryTreeNode<T> node, Consumer<T> callback, boolean asc) {
    if (node == null)
      return;

    BinaryTreeNode<T> first;
    BinaryTreeNode<T> last;

    if (asc) {
      first = node.left;
      last = node.right;
    } else {

      first = node.right;
      last = node.left;
    }

    this.inOrderTraverseNode(first, callback, asc);
    callback.accept(node.key);
    this.inOrderTraverseNode(last, callback, asc);
  }

  public void remove(T key) {
    this.root = this.removeNode(this.root, key);
  }

  public BinaryTreeNode<T> min() {
    return this.minNode(this.root);
  }

  public BinaryTreeNode<T> max() {
    return this.maxNode(this.root);
  }

  private BinaryTreeNode<T> minNode(BinaryTreeNode<T> node) {
    BinaryTreeNode<T> current = node;

    while(current.left != null) {
      current = current.left;
    }

    return current;
  }

  private BinaryTreeNode<T> maxNode(BinaryTreeNode<T> node) {
    BinaryTreeNode<T> current = node;

    while(current.right != null) {
      current = current.right;
    }

    return current;
  }

  private BinaryTreeNode<T> removeNode(BinaryTreeNode<T> node, T key) {
    if (node == null)
      return null;

    int comparasion = this.nodeComparator.compare(key, node.key);

    if(comparasion < 0) {
      node.left = this.removeNode(node.left, key);

      return node;
    } else if (comparasion > 0) {
      node.right = this.removeNode(node.right, key);

      return node;
    } else {
      if(node.right == null) return node.left;
      if(node.left == null) return node.right;

      BinaryTreeNode<T> aux = this.minNode(node.right);
      node.key = aux.key;
      this.removeNode(node.right, aux.key);
    }

    return node;
  }
}
