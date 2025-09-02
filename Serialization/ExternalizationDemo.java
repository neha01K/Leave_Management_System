import java.io.*;

class AnyClass implements Externalizable {
    String str;
    int i;
    int j;

    @Override
    public void writeExternal(ObjectOutput out)throws IOException {
        out.writeObject(str);
        out.writeInt(i);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         str = (String)in.readObject();
         i = (int)in.readInt();

    }

    public AnyClass(String str, int i, int j){
        this.str = str;
        this.i = i;
        this.j = j;
    }

    public AnyClass(){
        System.out.println("No-argument constructor!");
    }
}
public class ExternalizationDemo {
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {

        AnyClass obj = new AnyClass("ok",1,2);
        System.out.println(obj.str+"...."+obj.i+"......"+obj.j);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("file1.ser"));
        objectOutputStream.writeObject(obj);

        System.out.println("Deserialization Started");
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("file1.ser"));
        AnyClass obj1 = (AnyClass)objectInputStream.readObject();

        System.out.println(obj1.str+"....."+obj1.i+"....."+obj1.j);
    }
}
