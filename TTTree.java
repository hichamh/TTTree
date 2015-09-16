import java.io.PrintWriter;


public class TTTree 
{
	TTNode root;
    int numNodes;
    TTTree()
    {
        root = new TTNode();
        numNodes = 0;
    }
    TTTree(TTNode n)
    {
        root = n;
        numNodes = 1;
    }
    /*
     * adds a key to the tree
     * key is an integer
     * returns true if successful
     * returns false if the key is already present in the tree.
     */
    public boolean add(int item)
    {
    	TTNode spot = new TTNode();
        spot = this.find(this.root,item);
        if(spot == null)
        {
        	return false;
        }
        else
        {
            TTNode newLeaf = new TTNode(item,-1,spot,null,null,null);
            this.leafInsert(spot,newLeaf);
            return true;
        }
    	
    }
    /*
     * Inserts a non leaf node to the tree
     */
    private void internalNodeInsert(TTNode spot,TTNode sibling)
    {
        TTNode parent = spot.parent;
        if(parent == null)
        {
            parent = new TTNode();
            if(spot.k1 < sibling.k1)
            {
                parent.setFirst(spot);
                parent.setSecond(sibling);
            }
            else
            {
                parent.setFirst(sibling);
                parent.setSecond(spot);
            }
            root = parent;
            updateTree(parent);
        }
        else if(parent.first != null && parent.third == null)
        {
            if(sibling.k1 < parent.first.k1)
            {
                parent.setThird(parent.second);
                parent.setSecond(parent.first);
                parent.setFirst(sibling);
            }
            else if(sibling.k1 < parent.second.k1)
            {
                parent.setThird(parent.second);
                parent.setSecond(sibling);
            }
            else
            {
                parent.setThird(sibling);
            }
            updateTree(parent);

        }
        else if(parent.third != null)
        {
            TTNode newParentSibling = new TTNode();
            if(sibling.k1 < parent.first.k1)
            {
                newParentSibling.setFirst(parent.second);
                newParentSibling.setSecond(parent.third);
                parent.setSecond(parent.first);
                parent.setFirst(sibling);
                parent.third = null;
                //maybe update here both, will figure it out
            }
            else if(sibling.k1 < parent.second.k1)
            {
                newParentSibling.setFirst(parent.second);
                newParentSibling.setSecond(parent.third);
                parent.setSecond(sibling);
                parent.third = null;
            }
            else if(sibling.k1 < parent.third.k1)
            {
                newParentSibling.setFirst(sibling);
                newParentSibling.setSecond(parent.third);
                parent.third = null;

            }
            else
            {
                newParentSibling.setFirst(parent.third);
                newParentSibling.setSecond(sibling);
                parent.third = null;

            }
            updateTree(newParentSibling);
            updateTree(parent);
            internalNodeInsert(parent,newParentSibling);
        }
        
    }
    private int findSubTreeMin(TTNode node)
    {
        if(node.isLeaf())
        {
            return node.k1;
        }
        return findSubTreeMin(node.first);
    }
    private void updateTree(TTNode node)
    {
        if(node == null)
            return;
        else
        {
            if(node.second != null)
            {
                int subTreeMin = findSubTreeMin(node.second);
                node.k1 = subTreeMin;
            }
            else
            {
                node.k1 = -1;
            }
            if(node.third != null)
            {
                int subTreeMin = findSubTreeMin(node.third);
                node.k2 = subTreeMin;
            }
            else
            {
                node.k2 = -1;
            }
            updateTree(node.parent);
        }
    }
    public boolean search(int item)
    {
    	return this.find(this.root,item)==null?true:false;
    }
    /*
     * This method returns null if the data element exists otherwise it returns the parent where to insert a node.
     * 
     */
    private TTNode find(TTNode spot,int data)
    {
    	if (data == spot.k1 || data == spot.k2)
            return null;
    	if(spot.first != null)
    		if( data == spot.first.k1 || data == spot.first.k2)
    			return null;
    	if(spot.second != null)
    		if( data == spot.second.k1 || data == spot.second.k2)
    			return null;
    	if(spot.isLeaf() || spot.isKid1Leaf())
        {
    		return spot;
        }
        
            if(data < spot.k1)
                return find(spot.first,data);
            else if(spot.k2 == -1 || data < spot.k2)
                return find(spot.second,data);
            else //if(spot.k2 != -1 && data >= spot.k2)
                return find(spot.third,data);
        
    }
    private void leafInsert(TTNode spot, TTNode newLeaf)
    {
        if(spot.isLeaf())
        {
            spot.setFirst(newLeaf);
            updateTree(spot);
        }
        else if(spot.first != null && spot.second == null)
        {
            if(spot.first.k1 > newLeaf.k1)
            {
                spot.setSecond(spot.first);
                spot.setFirst(newLeaf);
            }
            else
            {
                spot.setSecond(newLeaf);
            }
            //update fields
            updateTree(spot);
        }
        else if(spot.first != null && spot.second != null && spot.third == null)
        {
            if(newLeaf.k1 < spot.first.k1)
            {
                spot.setThird(spot.second);
                spot.setSecond(spot.first);
                spot.setFirst(newLeaf);
            }
            else if(newLeaf.k1 < spot.second.k1)
            {
                spot.setThird(spot.second);
                spot.setSecond(newLeaf);
            }
            else
                spot.setThird(newLeaf);
            //update fields up and forth
            updateTree(spot);
        }
        else
        {
            TTNode sibling = new TTNode(-1,-1,spot.parent,null,null,null);
            if(newLeaf.k1 < spot.first.k1)
            {
                sibling.setFirst(spot.second);
                sibling.setSecond(spot.third);
                spot.third = null;
                spot.setSecond(spot.first);
                spot.setFirst(newLeaf);
            }
            else if(newLeaf.k1 < spot.second.k1)
            {
                sibling.setFirst(spot.second);
                sibling.setSecond(spot.third);
                spot.third = null;
                spot.setSecond(newLeaf);
            }
            else if(newLeaf.k1 < spot.third.k1)
            {
                sibling.setFirst(newLeaf);
                sibling.setSecond(spot.third);
                spot.third = null;
            }
            else
            {
                sibling.setFirst(spot.third);
                sibling.setSecond(newLeaf);
                spot.third = null;
            }
            updateTree(sibling);
            updateTree(spot);
            internalNodeInsert(spot,sibling);
            //put sibling in tree with internalNodeInsert
            //update spot and sibling keys and up and forth


        }
    }
    
    public void printToFile(PrintWriter out)
    {
    	out.println("------------------------------------------------------------");
    	this.printTree(this.root,0,out);
    	out.println("------------------------------------------------------------");
    }
    
    private void printTree(TTNode node,int depth,PrintWriter outputFile)
    {
       if(node == null)
       {
 
    	   return;
       }
        int pk1 = node.parent!=null?node.parent.k1:-1;
        int fk1 = node.first!=null?node.first.k1:-1 ;
        int sk1 = node.second!=null?node.second.k1:-1 ;
        int tk1 = node.third!=null?node.third.k1:-1 ;
        
        outputFile.print("Node : ")  ;
        outputFile.print(" depth = ") ;
        outputFile.println(depth) ;

        outputFile.print("key 1 : " + node.k1 + " ,");

        outputFile.print(" key 2 : " + node.k2 + " ,");

        outputFile.print(" parent key 1: " + pk1) ;
        outputFile.print(" ,");

        outputFile.print(" first node key 1 : " + fk1);
        outputFile.print(" ,");

        outputFile.print(" second node key 1 : " + sk1);
        outputFile.print(" ,");

        outputFile.print(" third node key 1 : " + tk1);
        outputFile.println(" ");
       printTree(node.first,depth+1,outputFile);
       printTree(node.second,depth+1,outputFile);
       printTree(node.third,depth+1,outputFile);

    }
    
    private class TTNode 
    {
    	int k1;
        int k2;

        TTNode first;
        TTNode second;
        TTNode third;
        TTNode parent;
        TTNode()
        {
            k1 = -1;
            k2 = -1;

            first = null;
            second = null;
            third = null;
            parent = null;
        }
        TTNode(int K1, int K2,TTNode Parent,TTNode First,TTNode Second,TTNode Third)//for constructing leafs
        {
            k1 = K1;
            k2 = K2;
            first = First;
            second = Second;
            third = Third;
            parent = Parent;
        }
        boolean isLeaf()
        {
            if(first == null && second == null)
            {
                return true;
            }
            return false;
        }
        boolean isKid1Leaf()
        {
            return first.isLeaf();
        }
        void setFirst(TTNode node)
        {
            first = node;
            node.parent = this;
        }
        void setSecond(TTNode node)
        {
            second = node;
            node.parent = this;
        }
        void setThird(TTNode node)
        {
            third = node;
            node.parent = this;
        }


    }

}
