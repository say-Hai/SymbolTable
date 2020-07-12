import java.io.*;
import java.util.Arrays;

public class ST<Key extends Comparable<Key>, Value> {
    private Key[] keys;         //键数组
    private  Value[] values;    //值数组
    private int size = 0;       //符号表元素个数
    //自动扩大keys[]和values[]数组的大小。
    public void resize(int max)
    {
        Key[] k = (Key[]) new Comparable[max];
        Value[] v = (Value[]) new Comparable[max];
        for(int i = 0; i < size; i++)
        {
            k[i] = keys[i];
            v[i] = values[i];
        }
        keys = k;
        values =v;
    }
    //构造函数，形成符号表
    public ST()
    {
        keys = (Key[]) new Comparable[1];
        values = (Value[]) new Comparable[1];
    }
    //获取键key对应的value值并返回。
    public Value get(Key key)
    {
        if(isEmpty() || key == null)    //如果为空或键值不存在则返回null。
        {
            return null;
        }
        int i = rank(key);      //找到键key对应的序列。
        if ((i < size) && (keys[i].compareTo(key) == 0))
        {
            return values[i];
        }
        return null;
    }
    //插入<key,value>
    public void put(Key key, Value value)
    {
        if(key == null)
            return;
        int i = rank(key);  //找到插入符号表的位置
        //如果符号表已经放满，则扩大表的大小
        if(size == keys.length)
        {
            resize(2*keys.length);
        }
        //如果已经存在，则交换值。避免重复值
        if(i < size && keys[i].compareTo(key) == 0)
        {
            values[i] = value;
            return;
        }
        //否则，将大于i的键序列往右移，空出i将键值对插入。
        for(int j = size; j>i; j--)
        {
            keys[j] = keys[j-1];
            values[j] = values[j-1];
        }
        keys[i] = key;
        values[i] = value;
        size++;
    }
    //对键值对进行排序
    private int rank(Key key) {
        int lo = 0;
        int hi = size -1;
        //二分查找
        while (lo <= hi)
        {
            int mid = lo + (hi - lo)/2;
            int cmd = key.compareTo(keys[mid]);
                if(cmd == 0)
                    return mid;
                else if(cmd < 0)
                    hi = mid - 1;
                else
                    lo = mid + 1;
        }
        return lo;
    }
    //表中所有键的集合，转为链表
    private Iterable<Key> keys()
    {
        return Arrays.asList(keys);
    }

    private boolean isEmpty() {
        return size == 0;
    }
    //key键是否已存在表中
    private boolean contain(Key key) {
        for(int i = 0; i<size ;i++)
        {
            if(keys[i].compareTo(key) == 0)
                return true;
        }
        return false;
    }
    public static void main(String[] args)
    {
        ST<String,Integer> st = new ST<String,Integer>();
        String  s0;
        String s1;
        try (BufferedReader br = new BufferedReader(new FileReader("test.txt")))
        {
         while((s0 = br.readLine()) != null)
         {
            String arr1[] = s0.split(" ");
            for(int i = 0; i< arr1.length;i++)
            {
                if (!st.contain(arr1[i]))
                    st.put(arr1[i],1);
                else{
                    st.put(arr1[i], st.get(arr1[i]) + 1);
                }
            }
         }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileWriter fw = new FileWriter("text.txt"))
        {
            for (String str : st.keys())
            {
                if(str == null)
                    continue;
                s1 = str + "  " + st.get(str) + "\n";

                fw.write(s1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
