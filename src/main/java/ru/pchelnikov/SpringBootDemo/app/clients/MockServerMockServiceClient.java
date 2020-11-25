package ru.pchelnikov.SpringBootDemo.app.clients;

import lombok.extern.slf4j.Slf4j;
import ru.pchelnikov.SpringBootDemo.app.dtos.MockServerUpdateDTO;
import ru.pchelnikov.SpringBootDemo.app.dtos.MockServerUserDTO;
import ru.pchelnikov.SpringBootDemo.app.exceptions.MockServerException;
import ru.pchelnikov.SpringBootDemo.interfaces.IMockServerServiceClient;

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
    public boolean update(UUID id, MockServerUpdateDTO mockServerUpdateDTO) {
        if (!UuidToDto.containsKey(id)) {
            return false;
        } else {
            MockServerUserDTO mockServerUserDTO = UuidToDto.get(id);
            mockServerUserDTO.chatId = mockServerUpdateDTO.chatId;
            mockServerUserDTO.birthDay = mockServerUpdateDTO.birthDay;
            mockServerUserDTO.phone = mockServerUpdateDTO.phone;
            return true;
        }
    }

    @Override
    public MockServerUserDTO read(String phone) {
        Optional<MockServerUserDTO> user = readAll().stream()
                .filter(dto -> phone.equals(dto.phone))
                .findFirst();
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new MockServerException("No user has been found: phone=" + phone);
        }
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
