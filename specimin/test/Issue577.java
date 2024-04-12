class Banana extends Apple<int[]> {
    class InnerBanana extends InnerApple {
        @Override
        // checking this override causes error
        // must be method in inner class.
        void foo(int[] array) {
        }
    }
}

class Apple<T> {
    class InnerApple {
        void foo(T param) {
        }
    }
}