package shopping.external;

import org.springframework.stereotype.Component;

@Component
public class PurgoMalumProfanityChecker implements ProfanityChecker {

    @Override
    public boolean containsProfanity(String text) {
        return false;
    }
}
