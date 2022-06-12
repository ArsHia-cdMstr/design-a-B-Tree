import java.security.cert.TrustAnchor;

public class B_Tree {
    private final int m ; // order of the B Tree


    // Node class Definition
    public class Node {

        int[] key = new int[m] ;
        Node[] child = new Node [m] ;
        int n = 0; // keys number
        boolean leaf = false ; // the node has children or not ? it has -> isn't leaf


    }


    private Node root ;

    // B Tree constructor
    public B_Tree(int m) {
        this.m = m;
        root = new Node();
        root.n = 0 ;
        root.leaf = true ;
    }

    public void shift_right(int ind , int[] keys, int keys_num){
        for (int i = ind ; i < ind + keys_num ; i++){
            keys [i+1] = keys[i] ;
        }
    }

    public void insert(int key_Value){
        Node root_copy = root ;
        if (root.n == m-1){
            //make a new root node
            Node new_root = new Node();
            root = new_root ;
            new_root.n = 0 ;
            new_root.leaf = false ;
            new_root.child[0] = root_copy ;
            split(new_root , root_copy);
        }
        else{
            //root node remain constant
            insert_node(root, key_Value);
        }
    }

    public void insert_node(Node parent, Node node , int key_value ){
        //find the node we want to put key in it
        int key_Value_index ;
        for(key_Value_index = 0 ; node.key[key_Value_index] < key_value ; key_Value_index++);

        if (node.leaf) {
            if (node.n == m - 1) {
                // insert in full node
                split(parent, node, key_value);
            } else {
                // insert in non full node

                shift_right(key_Value_index, node.key, node.n);
                node.key[key_Value_index] = key_value ;
            }
        }else{
            // find the suitable child node for key value
            insert_node(node, node.child[key_Value_index], key_value);
        }
    }


    public void split (Node parent ,Node child , int new_key_value){


    }





}
