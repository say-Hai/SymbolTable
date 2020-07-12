import java.io.*;
import java.util.Arrays;

public class ST<Key extends Comparable<Key>, Value> {
    private Key[] keys;         //������
    private  Value[] values;    //ֵ����
    private int size = 0;       //���ű�Ԫ�ظ���
    //�Զ�����keys[]��values[]����Ĵ�С��
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
    //���캯�����γɷ��ű�
    public ST()
    {
        keys = (Key[]) new Comparable[1];
        values = (Value[]) new Comparable[1];
    }
    //��ȡ��key��Ӧ��valueֵ�����ء�
    public Value get(Key key)
    {
        if(isEmpty() || key == null)    //���Ϊ�ջ��ֵ�������򷵻�null��
        {
            return null;
        }
        int i = rank(key);      //�ҵ���key��Ӧ�����С�
        if ((i < size) && (keys[i].compareTo(key) == 0))
        {
            return values[i];
        }
        return null;
    }
    //����<key,value>
    public void put(Key key, Value value)
    {
        if(key == null)
            return;
        int i = rank(key);  //�ҵ�������ű��λ��
        //������ű��Ѿ��������������Ĵ�С
        if(size == keys.length)
        {
            resize(2*keys.length);
        }
        //����Ѿ����ڣ��򽻻�ֵ�������ظ�ֵ
        if(i < size && keys[i].compareTo(key) == 0)
        {
            values[i] = value;
            return;
        }
        //���򣬽�����i�ļ����������ƣ��ճ�i����ֵ�Բ��롣
        for(int j = size; j>i; j--)
        {
            keys[j] = keys[j-1];
            values[j] = values[j-1];
        }
        keys[i] = key;
        values[i] = value;
        size++;
    }
    //�Լ�ֵ�Խ�������
    private int rank(Key key) {
        int lo = 0;
        int hi = size -1;
        //���ֲ���
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
    //�������м��ļ��ϣ�תΪ����
    private Iterable<Key> keys()
    {
        return Arrays.asList(keys);
    }

    private boolean isEmpty() {
        return size == 0;
    }
    //key���Ƿ��Ѵ��ڱ���
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
