package com.liu.solution;

/**
 * 红黑树
 */
public class RBTreeStudy  <T extends Comparable<T>> {

    private RBTNode<T> mRoot;   //根节点

    private static final boolean RED = false;
    private static final boolean BLACKR = true;


    public class RBTNode<T extends Comparable<T>>{
        boolean color;
        T key;
        RBTNode<T> left;
        RBTNode<T> right;
        RBTNode<T> parent;

        public RBTNode(T key,boolean color,RBTNode<T> parent,RBTNode<T> left, RBTNode<T> right){
            this.color = color;
            this.key = key;
            this.left= left;
            this.right = right;
            this.parent = parent;
        }

    }


/*
        * 对红黑树的节点(x)进行左旋转
 *
         * 左旋示意图(对节点x进行左旋)：
            *      px                              px
 *     /                               /
         *    x                               y
 *   /  \      --(左旋)-.           / \                #
            *  lx   y                          x  ry
 *     /   \                       /  \
         *    ly   ry                     lx  ly
 *
         *
         */
    private void leftRotate(RBTNode<T> x) {
        // 设置x的右孩子为y
        RBTNode<T> y = x.right;

        // 将 “y的左孩子” 设为 “x的右孩子”；
        // 如果y的左孩子非空，将 “x” 设为 “y的左孩子的父亲”
        x.right = y.left;
        if (y.left != null)
            y.left.parent = x;

        // 将 “x的父亲” 设为 “y的父亲”
        y.parent = x.parent;

        if (x.parent == null) {
            this.mRoot = y;            // 如果 “x的父亲” 是空节点，则将y设为根节点
        } else {
            if (x.parent.left == x)
                x.parent.left = y;    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
            else
                x.parent.right = y;    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
        }

        // 将 “x” 设为 “y的左孩子”
        y.left = x;
        // 将 “x的父节点” 设为 “y”
        x.parent = y;
    }

    /*
            * 对红黑树的节点(y)进行右旋转
 *
         * 右旋示意图(对节点y进行左旋)：
            *            py                               py
 *           /                                /
         *          y                                x
 *         /  \      --(右旋)-.            /  \                     #
            *        x   ry                           lx   y
 *       / \                                   / \                   #
         *      lx  rx                                rx  ry
 *
         */
    private void rightRotate(RBTNode<T> y) {
        // 设置x是当前节点的左孩子。
        RBTNode<T> x = y.left;

        // 将 “x的右孩子” 设为 “y的左孩子”；
        // 如果"x的右孩子"不为空的话，将 “y” 设为 “x的右孩子的父亲”
        y.left = x.right;
        if (x.right != null)
            x.right.parent = y;

        // 将 “y的父亲” 设为 “x的父亲”
        x.parent = y.parent;

        if (y.parent == null) {
            this.mRoot = x;            // 如果 “y的父亲” 是空节点，则将x设为根节点
        } else {
            if (y == y.parent.right)
                y.parent.right = x;    // 如果 y是它父节点的右孩子，则将x设为“y的父节点的右孩子”
            else
                y.parent.left = x;    // (y是它父节点的左孩子) 将x设为“x的父节点的左孩子”
        }

        // 将 “y” 设为 “x的右孩子”
        x.right = y;

        // 将 “y的父节点” 设为 “x”
        y.parent = x;
    }





}
