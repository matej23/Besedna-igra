public class Triple<A, B, C> {
    private final A mFirst;
    private final B mSecond;
    private final C mThird;

    public Triple(final A first, final B second, final C third) {
        this.mFirst = first;
        this.mSecond = second;
        this.mThird = third;
    }

    public A getFirst() {
        return this.mFirst;
    }

    public B getSecond() {
        return this.mSecond;
    }

    public C getThird() {
        return this.mThird;
    }
}