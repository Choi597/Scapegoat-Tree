public class T {
    int a;
    int b;

    public T () {
        this.a = 0;
        this.b = 0;
    }

    public T (int a) {
        this.a = a;
        this.b = 0;
    }

    public T (int a, int b) {
        this.a = a;
        this.b = b;
    }


    public int compareTo (T t2) {
        // check if this instance is lexicographic smaller than t2
        // return -1 if this < t2;
        // return 1 if this > t2;
        // return 0 if this == t2;

        if ((this.a < t2.a) || ((this.a == t2.a) && (this.b < t2.b))) {
            return -1;
        } else if ((this.a > t2.a) || ((this.a == t2.a) && (this.b > t2.b))) {
            return 1;
        } else {
            return 0;
        }

    }

    public String toString() {
        return String.valueOf(this.a) + ' ' + String.valueOf(this.b);
    }

}
