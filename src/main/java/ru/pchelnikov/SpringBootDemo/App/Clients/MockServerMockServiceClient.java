package ru.pchelnikov.SpringBootDemo.App.Clients;

import lombok.extern.slf4j.Slf4j;
import ru.pchelnikov.SpringBootDemo.App.DTOs.MockServerUserDTO;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IMockServerServiceClient;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
public class MockServerMockServiceClient implements IMockServerServiceClient {

    private final Map<UUID, MockServerUserDTO> UuidToDto = new HashMap<>();

    @PostConstruct
    private void init() {
        UUID id = UUID.randomUUID();
        MockServerUserDTO dto = new MockServerUserDTO();
        dto.birthDay = new Date();
        dto.chatId = "123456";
        dto.firstName = "firstName";
        dto.middleName = "middleName";
        dto.secondName = "secondName";
        dto.id = id;
        dto.male = true;
        dto.phone = "123456";
        UuidToDto.put(id, dto);
    }

    @Override
    public UUID create(MockServerUserDTO mockServerUserDTO) {
        UUID id = UUID.randomUUID();

        MockServerUserDTO mockServerUserDTO1 = new MockServerUserDTO();
        mockServerUserDTO1.birthDay = mockServerUserDTO.birthDay;
        mockServerUserDTO1.chatId = mockServerUserDTO.chatId;
        mockServerUserDTO1.firstName = mockServerUserDTO.firstName;
        mockServerUserDTO1.middleName = mockServerUserDTO.middleName;
        mockServerUserDTO1.secondName = mockServerUserDTO.secondName;
        mockServerUserDTO1.id = id;
        mockServerUserDTO1.male = mockServerUserDTO.male;
        mockServerUserDTO1.phone = mockServerUserDTO.phone;

        UuidToDto.put(id, mockServerUserDTO1);
        return id;
    }

    @Override
    public MockServerUserDTO read(UUID id) {
        return UuidToDto.get(id);
    }

    @Override
    public MockServerUserDTO read(String phone) {
        return readAll().stream()
                .filter(dto -> phone.equals(dto.phone))
                .findFirst()
                .get();
    }

    @Override
    public List<MockServerUserDTO> readAll() {
        return new ArrayList<>(UuidToDto.values());
    }

    @Override
    public boolean hasUser(String phone) {
        return UuidToDto.values().stream()
                .anyMatch(dto -> phone.equals(dto.phone));
    }

    @Override
    public boolean hasUser(UUID id) {
        return UuidToDto.containsKey(id);
    }
}
