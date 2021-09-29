import java.util.ArrayList;

public class Sorting<Item extends Comparable<Item>> {


    public Sorting() {
    }

    public boolean greaterThan(ArrayList<Item> list, int i, int j) {
        return list.get(i).compareTo(list.get(j)) > 0;
    }

    public boolean greaterThan(Item i, Item j) {
        return i.compareTo(j) > 0;
    }

    public boolean lessThan(ArrayList<Item> list, int i, int j) {
        return list.get(i).compareTo(list.get(j)) < 0;
    }

    public boolean lessThan(Item i, Item j) {
        return i.compareTo(j) < 0;
    }

    public boolean equal(Item i, Item j) {
        return i.compareTo(j) == 0;
    }

    public boolean equal(ArrayList<Item> list, int i, int j) {
        return list.get(i).compareTo(list.get(j)) == 0;
    }

    public ArrayList<Item> insertionSort(ArrayList<Item> list) {
        if(list == null || list.size() == 0) {
            return null;
        }
        if(list.size() == 1) {
            return list;
        }
        int size = list.size();
        for(int i = 1; i < size; i++) {
            int j = i-1;
            while(j >= 0 && greaterThan(list.get(j), list.get(j+1))) {
                swap(list, j, j+1);
                j-=1;
            }
        }
        return list;
    }

    public ArrayList<Item> mergeSort(ArrayList<Item> list) {
        if(list == null || list.size() == 0) {
            return null;
        }
        if(list.size() == 1) {
            return list;
        }
        ArrayList<Item> listA = new ArrayList<Item>();
        int x = list.size()/2 + (list.size()%2);
        for(int i = 0; i < x; i++){
            listA.add(list.get(i));
        }
        ArrayList<Item> listB = new ArrayList<Item>();
        int y = list.size();
        for(int i = x; i < y; i++){
            listB.add(list.get(i));
        }

        mergeSort(listA);
        mergeSort(listB);

        int listAPointer = 0;
        int listBPointer = 0;
        int listPointer = 0;
        while(listAPointer < listA.size() && listBPointer < listB.size()) {
            if(greaterThan(listB.get(listBPointer), listA.get(listAPointer))) {
                list.set(listPointer, listA.get(listAPointer));
                listAPointer++;
                listPointer++;
            } else {
                list.set(listPointer, listB.get(listBPointer));
                listBPointer++;
                listPointer++;
            }
        }
        while(listAPointer < listA.size()) {
            list.set(listPointer, listA.get(listAPointer));
            listAPointer++;
            listPointer++;
        }
        while(listBPointer < listB.size()) {
            list.set(listPointer, listB.get(listBPointer));
            listBPointer++;
            listPointer++;
        }
        return list;
    }

    public void print(ArrayList<Item> list) {
        int n = list.size();

        StringBuffer bf = new StringBuffer();
        bf.append(list.get(0).toString());

        for (int i = 1;i < n; i += 1) {
            bf.append(" " + list.get(i).toString());
        }

        System.out.println(bf.toString());
    }

    private void swap(ArrayList<Item> list, int i, int j) {
        Item temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public static void main(String[] args) {

        Sorting<Integer> s = new Sorting<>();
        ArrayList<Integer> A = new ArrayList<Integer>();
        Integer[] K = {4,4,3,1,3,9,8,2,5,6};

        for (Integer k : K) {
            A.add(k);
        }
        s.print(A);
        System.out.println(s.mergeSort(A));
        System.out.println(s.insertionSort(A));

    }

}
