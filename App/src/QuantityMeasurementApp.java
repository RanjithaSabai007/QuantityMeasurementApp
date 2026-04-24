public class QuantityMeasurementApp {

    enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0),
        YARD(3.0),
        CENTIMETER(0.0328084);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

    static class QuantityLength {
        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid numeric value");
            }
            this.value = value;
            this.unit = unit;
        }

        public double toBase() {
            return unit.toFeet(value);
        }

        public QuantityLength convertTo(LengthUnit targetUnit) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }
            double base = toBase();
            double converted = targetUnit.fromFeet(base);
            return new QuantityLength(converted, targetUnit);
        }

        // UC6: default (result in first operand unit)
        public QuantityLength add(QuantityLength other) {
            if (other == null) {
                throw new IllegalArgumentException("Other length cannot be null");
            }

            double sumBase = this.toBase() + other.toBase();
            double result = this.unit.fromFeet(sumBase);

            return new QuantityLength(result, this.unit);
        }

        // UC7: explicit target unit
        public static QuantityLength add(QuantityLength l1, QuantityLength l2, LengthUnit targetUnit) {
            if (l1 == null || l2 == null) {
                throw new IllegalArgumentException("Operands cannot be null");
            }
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double sumBase = l1.toBase() + l2.toBase();
            double result = targetUnit.fromFeet(sumBase);

            return new QuantityLength(result, targetUnit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;
            return Double.compare(this.toBase(), other.toBase()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toBase());
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    public static void main(String[] args) {

        QuantityLength a = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength b = new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println(QuantityLength.add(a, b, LengthUnit.FEET));      // 2 FEET
        System.out.println(QuantityLength.add(a, b, LengthUnit.INCH));      // 24 INCH
        System.out.println(QuantityLength.add(a, b, LengthUnit.YARD));      // ~0.667 YARD

        QuantityLength c = new QuantityLength(2.54, LengthUnit.CENTIMETER);
        QuantityLength d = new QuantityLength(1.0, LengthUnit.INCH);

        System.out.println(QuantityLength.add(c, d, LengthUnit.CENTIMETER)); // ~5.08 CM
    }
}