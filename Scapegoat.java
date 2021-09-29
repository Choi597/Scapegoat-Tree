
/**
 * CS 251: Data Structures and Algorithms
 * Project 3: Part 1
 *
 * TODO: implement scapegoat.
 *
 * @author David Choi
 * @username Choi597
 * @sources NA
 *
 * Use the algorithms written in sorting to implement this class.
 *
 */

import java.sql.SQLOutput;
import java.util.*;

public class Scapegoat {

    // Root node
    private Node root;
    private Node newbie;
    // max node count required to implement deletion.
    private int MaxNodeCount = 0;
    // total node number
    private int NodeCount = 0;
    // parameter alpha
    private static final double threshold = 0.57;

    public class Node {
        T data;
        Node parent;
        Node left;
        Node right;

        public Node(T data, Node parent, Node left, Node right) {
            this.data = data;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public String toString() {
            return "[data=" + data + "]";
        }
    }

    public Scapegoat() {
        root = null;
    }

    public Scapegoat(T data) {
        root = new Node(data, null, null, null);
        NodeCount++;
    }

    public Node root() {
        return this.root;
    }

    private Node scapegoatNode(Node node) {
        while (true) {
//            System.out.println("***************");
//            System.out.println("node size: " + size(node));
//            System.out.println("node " + node.toString());
//            System.out.println("parent node size: " + size(node.parent));
//            System.out.println("node parent: " + node.parent);
//            System.out.println("=================");
            if (size(node) > threshold * size(node.parent)) {
                return node.parent;
            } else {
                node = node.parent;
            }
        }
    }


    public Node toBST(List<Node> list, int front, int back) {
        if (front > back) {
            return null;
        }
        int middle = (front + back +1) / 2;
        Node middleNode = list.get(middle);
        middleNode.left = toBST(list, front, middle -1);
        if(middleNode.left != null) {
            middleNode.left.parent = middleNode;
        }
        middleNode.right = toBST(list, middle + 1, back);
        if(middleNode.right != null) {
            middleNode.right.parent = middleNode;
        }
        return middleNode;
    }

    public Node rebuild(Node node) {
        List<Node> inorderList = inorder(node);
        if (node.parent == null) {
            MaxNodeCount = NodeCount;
            root = toBST(inorderList, 0, inorderList.size() - 1);
            return root;
        }
        Node originalParent = node.parent;
        Node balanced = toBST(inorderList, 0, inorderList.size() - 1);
        balanced.parent = originalParent;
        if (originalParent.data.compareTo(balanced.data) == 1) {
            originalParent.left = balanced;
        } else {
            originalParent.right = balanced;
        }
        return balanced;
    }

    public Node BSTadd(Node node, T data) {
        if (node == null) {
            node = new Node(data, null, null, null);
            NodeCount++;
            MaxNodeCount = Math.max(NodeCount, MaxNodeCount);
            return node;
        }
        if (data.compareTo(node.data) == -1) {
            node.left = BSTadd(node.left, data);
            node.left.parent = node;
        } else if (data.compareTo(node.data) == 1) {
            node.right = BSTadd(node.right, data);
            node.right.parent = node;
        }
        return node;
    }

    public int getHeight(Node node) {
        if (node == null) {
            return -1;
        }
        int height = 0;
        int LeftHeight = getHeight(node.left);
        int RightHeight = getHeight(node.right);
        height = Math.max(LeftHeight, RightHeight) + 1;
        return height;
    }

    public void add(T data) {
        root = BSTadd(root, data);
        if (getHeight(root) > -1 * Math.log(size(root))/Math.log(threshold)) {
            //System.out.println("scapeGoat----------------------------------");
            Node addedNode = find(data);
            //System.out.println(addedNode);
            //System.out.println("added node parennt" + addedNode.parent);
            Node scapegoat = scapegoatNode(addedNode);
            //System.out.println(scapegoat);
            //look
            rebuild(scapegoat);
        }

    }

    public Node removeFromBST(Node node, T data) {
        if(node == null) {
            NodeCount--;
            return node;
        } else if(data.compareTo(node.data) == 1) {
            node.right = removeFromBST(node.right, data);
            if(node.right != null) {
                node.right.parent = node;
            }
        } else if(data.compareTo(node.data) == -1) {
            node.left = removeFromBST(node.left, data);
            if(node.left != null) {
                node.left.parent = node;
            }
        } else {
            if (node.right == null && node.left == null) {
                node = null;
                NodeCount--;
            } else if (node.right == null) {
                Node parent = node.parent;
                node = node.left;
                node.parent = parent;
                NodeCount--;
            } else if (node.left == null) {
                Node p = node.parent;
                node = node.right;
                node.parent = p;
                NodeCount--;
            } else {
                node.data = inorder(node.right).get(0).data;
                node.right = removeFromBST(node.right, inorder(node.right).get(0).data);
            }
        }
        return node;
    }

    public void remove(T data) {
        root = removeFromBST(root, data);
        if((float) NodeCount <= (float) threshold * (float) MaxNodeCount) {
            root = rebuild(root);
        }



    }

    // preorder traversal
    public List<Node> preorder(Node node) {
        List<Node> nodes = new ArrayList<Node>();
        nodes.add(node);
        if(node.left != null){
            nodes.addAll(preorder(node.left));
        }
        if(node.right != null){
            nodes.addAll(preorder(node.right));
        }
        return nodes;
    }


    // inorder traversal
    public List<Node> inorder(Node node) {
        List<Node> nodes = new ArrayList<Node>();
        if(node.left != null){
            nodes.addAll(inorder(node.left));
        }
        nodes.add(node);
        if(node.right != null){
            nodes.addAll(inorder(node.right));
        }
        return nodes;
    }

    // not used, but you can use this to debug
    public void print() {
        List<Node> nodes = inorder(root);
        for(int i=0;i<nodes.size();i++){
            System.out.println(nodes.get(i).toString());
        }
    }

    // return the node whose data is the same as the given data.
    public Node find(T data) {
        Node current = root;
        int result;
        while(current != null){
            result = data.compareTo(current.data);
            if(result == 0){
                return current;
            }else if(result > 0){
                current = current.right;
            }else{
                current = current.left;
            }
        }
        return null;
    }

    // find the succNode
    public Node succNode(Node node) {
        Node succ = null;
        int result;
        Node current = node;
        while(current != null){
            result = node.data.compareTo(current.data);
            if(result < 0){
                succ = current;
                current = current.left;
            }else{
                current = current.right;
            }
        }
        return succ;
    }

    // used in scapegoatNode function, not a delicated one...
    // Just the brute force calculating the node's children's nubmer. Can be accelerated.
    private int size (Node node) {
        if (node == null)
            return 0;
        return 1 + size(node.left) + size(node.right);
    }


    // BFS(not used, you may use this to help you debug)
    public List<Node> breadthFirstSearch() {
        Node node = root;
        List<Node> nodes = new ArrayList<Node>();
        Deque<Node> deque = new ArrayDeque<Node>();
        if(node != null){
            deque.offer(node);
        }
        while(!deque.isEmpty()){
            Node first = deque.poll();
            nodes.add(first);
            if(first.left != null){
                deque.offer(first.left);
            }
            if(first.right != null){
                deque.offer(first.right);
            }
        }
        return nodes;
    }

    public void printTree(Node n) {
        List<Node> inorder = inorder(n);
        for(int i = 0; i < inorder.size(); i++) {
            System.out.print(inorder.get(i).data + "----");
            if(inorder.get(i).parent != null) {
                System.out.print(inorder.get(i).parent.data);
                System.out.println("");
            } else {
                System.out.println("null");
            }
        }
    }
