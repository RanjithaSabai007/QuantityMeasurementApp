public class QuantityMeasurementApp {

    static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    public static boolean compareFeet(double value1, double value2) {
        return new Feet(value1).equals(new Feet(value2));
    }

    public static boolean compareInches(double value1, double value2) {
        return new Inches(value1).equals(new Inches(value2));
    }

    public static void main(String[] args) {
        System.out.println("Feet Equal: " + compareFeet(1.0, 1.0));
        System.out.println("Inches Equal: " + compareInches(1.0, 1.0));
    }
}