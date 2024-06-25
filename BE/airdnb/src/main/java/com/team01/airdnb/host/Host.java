package com.team01.airdnb.host;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "hosts")
public class Host {
  @Id
  private Long  id;

  @NotBlank(message = "계좌번호를 입력하세요")
  @Size(min = 10, max = 14, message = "계좌번호 10~14자 사이를 입력해주세요")
  private String accountNum;

  @OneToOne
  @MapsId
  @JoinColumn(name = "user_id")
  private User user;
}
