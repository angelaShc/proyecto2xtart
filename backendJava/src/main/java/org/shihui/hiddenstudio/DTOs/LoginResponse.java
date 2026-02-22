package org.shihui.hiddenstudio.DTOs;

import java.util.List;

public record LoginResponse(
        String token,
        String username,
        List<String> roles
) {}
