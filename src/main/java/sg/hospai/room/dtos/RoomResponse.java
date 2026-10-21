package sg.hospai.room.dtos;

public record RoomResponse(
        Long id,
        Long ward,
        Integer number,
        String roomCode
) {
}
