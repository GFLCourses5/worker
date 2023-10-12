package executor.service.service.impl.mapper;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.model.response.ProxyResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper utility class for mapping ProxyResponse objects to ProxyConfigHolder DTOs.
 * <p>
 * This utility class provides static methods to map ProxyResponse objects to
 * ProxyConfigHolder DTOs, allowing for easy conversion between different data types.
 *
 * @see ProxyConfigHolder
 * @see ProxyResponse
 * <p>
 *
 * @author Yurii Kotsiuba
 * @version 01
 */
@Component
public class ProxyResponseMapper {
    /**
     * Maps a ProxyResponse object to a ProxyConfigHolder DTO.
     *
     * @param response The ProxyResponse object to be mapped.
     * @return A ProxyConfigHolder DTO representing the mapped data.
     */
    public static ProxyConfigHolder toProxyDto(ProxyResponse response) {
        return new ProxyConfigHolder(getProxyNetworkConfig(response), getProxyCredentials(response));
    }

    private static ProxyNetworkConfig getProxyNetworkConfig(ProxyResponse response) {
        return new ProxyNetworkConfig(response.getHost(), response.getPort());
    }

    private static ProxyCredentials getProxyCredentials(ProxyResponse response) {
        return new ProxyCredentials(response.getUsername(), response.getPassword());
    }

    /**
     * Maps a list of ProxyResponse objects to a list of ProxyConfigHolder DTOs.
     *
     * @param responseList The list of ProxyResponse objects to be mapped.
     * @return The list of ProxyConfigHolder DTO representing the mapped data.
     */
    public static List<ProxyConfigHolder> toCollectionDto(List<ProxyResponse> responseList) {
        return responseList.stream()
                .map(ProxyResponseMapper::toProxyDto)
                .collect(Collectors.toList());
    }
}
