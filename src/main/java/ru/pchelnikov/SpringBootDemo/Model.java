package ru.pchelnikov.SpringBootDemo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Model {
    private String fio;
    private boolean isMale;
    private String phone;

}
