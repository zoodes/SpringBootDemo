package ru.pchelnikov.SpringBootDemo;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ChildModel extends Model {
    private final int age;
}
