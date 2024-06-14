package codesquad.airdnb.domain.member.repository;

import codesquad.airdnb.domain.member.Member;
import codesquad.airdnb.domain.member.MemberRepository;
import codesquad.airdnb.global.config.QuerydslConfig;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QuerydslConfig.class)
@ActiveProfiles("test")
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;


    private FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .plugin(new JakartaValidationPlugin())
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .build();


    @Test
    void 멤버를_저장하고_그_멤버를_가져올_수_있다() {
        // Given
        Member member = fixtureMonkey.giveMeBuilder(Member.class)
                .setNull("id")
                .sample();

        // When
        Member savedMember = memberRepository.save(member);
        Member foundMember = memberRepository.findById(savedMember.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 Id의 멤버가 없습니다."));

        // Then
        assertThat(member).isEqualTo(foundMember);
    }
}
