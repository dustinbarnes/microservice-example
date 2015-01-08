package com.github.dustinbarnes.microservice.photocache.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo
{
    String userid;
    String fileSystemPath;
    String webUrlPath;
    String caption;
}
