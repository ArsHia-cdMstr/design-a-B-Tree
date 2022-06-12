import java.security.cert.TrustAnchor;

public class B_Tree {
    private final int m; // order of the B Tree


    // Node class Definition
    public class Node {


        Node parent = null; // parent of this node
        int[] key = new int[m];
        Node[] child = new Node[m + 1];
        int n; // keys number
        boolean leaf = false; // the node has children or not ? it has -> isn't leaf


    }


    private Node root;

    // B Tree constructor
    public B_Tree(int m) {
        this.m = m;
        root = new Node();
        root.n = 0;
        root.leaf = true;
    }

    public void shift_right(int ind, int[] keys, int keys_num) {
        for (int i = keys_num; i > ind ; i--) {
            keys[i] = keys[i-1];
        }
    }


    public Node make_new_rootNode(Node root_copy) {
        Node new_root = new Node();
        root = new_root;
        new_root.n = 0;
        new_root.leaf = false;
        new_root.child[0] = root_copy;
        root_copy.parent = new_root;

        return new_root;
    }


    public void insert(int key_Value) {
        Node root_copy = root;
        if (root.n == m - 1 && root.leaf) {
            //make a new root node
            Node new_root = make_new_rootNode(root_copy);
            split_key(new_root, root_copy, key_Value);
        } else {
            //root node remain constant
            //root isn't full so it won't split so it doesn't matter what we give to insert_node func as (Node Parent)
            insert_node(root, root, key_Value);
        }
    }

    public void insert_node(Node parent, Node node, int key_value) {
        //find the node we want to put key in it
        int key_Value_index;
        for (key_Value_index = 0; node.n > key_Value_index && node.key[key_Value_index] < key_value; key_Value_index++)
            ;

        if (node.leaf) {
            if (node.n == m - 1) {
                // insert in full node
                split_key(parent, node, key_value);
            } else {
                // insert in non full node

                shift_right(key_Value_index, node.key, node.n);
                node.key[key_Value_index] = key_value;
                node.n++;
            }
        } else {
            // find the suitable child node for key value
            insert_node(node, node.child[key_Value_index], key_value);
        }
    }

    public void split_key(Node parent, Node child, int new_key_value) {
        // insert key value
        int key_Value_index;
        for (key_Value_index = 0; key_Value_index < child.n && child.key[key_Value_index] < new_key_value; key_Value_index++)
            ;

        shift_right(key_Value_index, child.key, child.n);
        child.key[key_Value_index] = new_key_value;
        child.n += 1;

        // split
        split(parent, child);

        // check the parent classifying
        if (parent.n == m) {
            if (parent.parent != null) {
                split(parent.parent, parent);
            } else {
                // make a new root node
                Node root_copy = root;
                Node new_root = make_new_rootNode(root_copy);
                split(new_root, root_copy);
            }
        }

    }

    //just splitting
    public void split(Node parent, Node child) {
        Node new_child_node = new Node();
        //manage child and keys
        int t = m / 2 + 1;
        int i;
        for (i = t; i < child.n; i++) {
            new_child_node.key[i - t] = child.key[i];
        }

        if (!child.leaf) {
            for (i = t; i <= child.n; i++) {
                new_child_node.child[i - t] = child.child[i];
            }
        }
        // manage n and leaf and parent
        new_child_node.n = t - 1;
        child.n = t - 1;
        new_child_node.leaf = child.leaf;
        new_child_node.parent = parent;


        // insert the middle key in child node in parent keys
        int j;
        for (j = 0; j < parent.n && parent.key[j] < child.key[t]; j++) ;
        shift_right(j, parent.key, parent.n);
        parent.key[j] = child.key[t-1];
        parent.n += 1;

        // shift the parent child to free the  j+1 index for new_child_node
        for (int z = parent.n; z > j+1 ; z--)
            parent.child[z] = parent.child[z-1];

        parent.child[j+1] = new_child_node ;
    }

    public void preordred_print(Node node) {
        for (int i = 0; i < node.n; i++) {
            System.out.println(node.key[i]);
        }
        if (!(node.leaf))
            for (int i = 0; i <= node.n; i++)
                preordred_print(node.child[i]);
    }

    public void start() {
        preordred_print(root);
    }

    public static void main(String[] args) {
        B_Tree b1 = new B_Tree(3);
        int[] test1 = {8, 11, 6, 7, 15, 12, 13, 4, 34, 10};
        for (int i = 0; i < 10; i++) {
            System.out.println("insert" + test1[i] + ": ");
            b1.insert(test1[i]);
        }
        b1.start();
    }

}
