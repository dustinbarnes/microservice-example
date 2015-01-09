package com.github.dustinbarnes.microservice.friends.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    String userid;
    String profileLocation;
}
