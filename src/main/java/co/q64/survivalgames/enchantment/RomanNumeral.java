package co.q64.survivalgames.enchantment;

public class RomanNumeral {

	enum Numeral {
		C(100), CD(400), CM(900), D(500), I(1), IV(4), IX(9), L(50), M(1000), V(5), X(10), XC(90), XL(40);
		int weight;

		Numeral(int weight) {
			this.weight = weight;
		}
	}

	;

	public static String convert(long n) {

		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		StringBuilder buf = new StringBuilder();

		final Numeral[] values = Numeral.values();
		for (int i = values.length - 1; i >= 0; i--) {
			while (n >= values[i].weight) {
				buf.append(values[i]);
				n -= values[i].weight;
			}
		}
		return buf.toString();
	}
}