import java.security.cert.TrustAnchor;

public class B_Tree {
    private final int m ; // order of the B Tree


    // Node class Definition
    public class Node {

        int[] key = new int[m-1] ;
        Node[] child = new Node [m] ;
        int n ; // keys number
        boolean leaf ; // the node has children or not ? it has -> isn't leaf
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

    public void insert(int insert_key){
        Node root_copy = root ;
        if (root.n == m-1){
            //make a new root node
            Node new_root = new Node();
            root = new_root ;
            new_root.n = 0 ;
            new_root.leaf = false ;
            new_root.child[0] = root_copy ;
            split(new_root , 0 , root_copy );
            insert_node(new_root , insert_key);

        }
        else{
            //root node remain constant
            insert_node(root, insert_key);
        }
    }

    public void insert_node(Node node , int key_value ){
        //find the node we want to put key in it

        if (node.leaf) {

            if (node.n == m - 1) {
                // insert in full node
                int i;
                for(i = 0 ; node.key[i] < key_value ; i++);
                split();
            } else {
                // insert in non full node
                int i ;
                for(i = 0 ; node.key[i] < key_value ; i++);
                shift_right(i, node.key, node.n);
                node.key[i] = key_value ;
            }
        }else{
            // find the suitable child node for key value
            int i ;
            for(i = 0 ; node.key[i] < key_value ; i++);
            insert_node(node.child[i] , key_value);
        }
    }


    public void split (Node parent ,int index_in_parent ,Node child){

    }





}
