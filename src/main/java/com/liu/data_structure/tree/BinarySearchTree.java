package com.liu.data_structure.tree;

/**
 * 二叉查找树
 *
 */
public class BinarySearchTree {

    private Node tree;

    /**
     * 查找
     * @param data
     * @return
     */
    public Node find(int data){
        Node p = tree;
        while (p!=null){
            if (data<p.data) p = p.left;
            else if (data> p.data) p = p.right;
            else return p;
        }
        return null;
    }

    public void insert(int data){

        if (tree==null){
            tree = new Node(data);
            return;
        }

        Node p = tree;
        while (p!=null){
            if (data>p.data){
                if (p.right == null){
                    p.right = new Node(data);
                    return;
                }
                p=p.right;
            }else if (data<p.data){
                if (p.left==null){
                    p.left = new Node(data);
                    return;
                }
                p = p.left;
            }

        }
    }




    private static class Node{
        private int data;
        private Node left;
        private Node right;

        public Node(int data){
            this.data = data;
        }
    }
}
