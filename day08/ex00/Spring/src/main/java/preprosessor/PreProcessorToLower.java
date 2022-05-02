package preprosessor;

public class PreProcessorToLower implements PreProcessor {

    @Override
    public String preProcess(String s) {
        return s.toLowerCase();
    }
}
