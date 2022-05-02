package preprosessor;

public class PreProcessorToUpperImpl implements PreProcessor {

    @Override
    public String preProcess(String s) {
        return s.toUpperCase();
    }
}
