package analyzer.practicum.telemetry.analyzer.controller;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;

@Service
@Slf4j
public class HubRouterController {
    private final HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterClient;

    public HubRouterController(@GrpcClient("hub-router")
                     HubRouterControllerGrpc.HubRouterControllerBlockingStub hubRouterClient) {
        this.hubRouterClient = hubRouterClient;
    }

    public void sendAction(DeviceActionRequest deviceActionRequest) {
        try {
            log.info("HUB REQUEST: {}", deviceActionRequest);
            hubRouterClient.handleDeviceAction(deviceActionRequest);
        } catch (Exception e) {
            log.error("Couldn't send data");
        }
    }
}
