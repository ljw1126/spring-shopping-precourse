package shopping.service;

public interface JwtProvider {

    String createToken(Long memberId);
}
