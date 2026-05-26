public class StatisticalAnalyzer {
    private final Cipher cipher;
    private final char[] alphabet;

    public StatisticalAnalyzer(Cipher cipher, char[] alphabet) {
        this.cipher = cipher;
        this.alphabet = alphabet;
    }

    public String decryptByStatisticalAnalysis(String encryptedText, String sampleText) {
        double[] sampleFreq = calcFrequency(sampleText.toLowerCase());
        double[] encryptedFreq = calcFrequency(encryptedText);

        int bestKey = 0;
        double minDeviation = Double.MAX_VALUE;

        for (int key = 0; key < alphabet.length; key++) {
            double deviation = 0.0;
            for (int i = 0; i < alphabet.length; i++) {
                int shiftedIndex = (i + key) % alphabet.length;
                double diff = sampleFreq[i] - encryptedFreq[shiftedIndex];
                deviation += diff * diff;
            }
            if (deviation < minDeviation) {
                minDeviation = deviation;
                bestKey = key;
            }
        }

        return cipher.decrypt(encryptedText, bestKey);
    }

    private double[] calcFrequency(String text) {
        int[] counts = new int[alphabet.length];
        int total = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int index = indexOf(c);
            if (index != -1) {
                counts[index]++;
                total++;
            }
        }
        double[] freq = new double[alphabet.length];
        if (total == 0) return freq;
        for (int i = 0; i < alphabet.length; i++) {
            freq[i] = (double) counts[i] / total;
        }
        return freq;
    }

    private int indexOf(char c) {
        for (int i = 0; i < alphabet.length; i++) {
            if (alphabet[i] == c) return i;
        }
        return -1;
    }
}