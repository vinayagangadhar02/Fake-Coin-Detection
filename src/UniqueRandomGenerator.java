import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UniqueRandomGenerator {
    int range;
    UniqueRandomGenerator(int range){
        this.range=range;
    }
    public int random() {

        List<Integer> numbers = new ArrayList<>();
        for (int i = 10; i < range; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);

        return numbers.get(0);
    }
}
