package com.example.axolotl.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(max = 255, message = "Tag too long, maximum is 255 symbols!")
    private String tag;

    @NotBlank(message = "please, write some text...")
    @Length(max = 2048, message = "Message too long")
    private String text;

    private String filename;

    private LocalDateTime time = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
}
