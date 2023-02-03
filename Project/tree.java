import java.util.ArrayList;
public class tree
{
    private Node root;
    tree(){
        root=null;

    }
    tree(Node root)
    {
        this.root=root;
    }
    public Node getRoot() {
        return root;
    }




    static void filltree(Node node ,ArrayList<String>arr,count i)
    {
        while (i.counter<arr.size())  {

            if (arr.get(i.counter).length()>=2&&arr.get(i.counter).charAt(1)=='/')
            {
                return;
            }
            else if(arr.get(i.counter).charAt(0)!='<'){
                node.setDataOfTag(arr.get(i.counter));
                i.counter=i.counter+1;
                return;
            }
            else {
                Node child=new Node();
                child.setDepth(node.getDepth()+1);
                child.setNameOfTag(arr.get(i.counter));
                Node.addrepetedChild(node,child);
                i.counter=i.counter+1;
                filltree(child,arr,i);
            }
            i.counter=i.counter+1;
        }
    }

}
