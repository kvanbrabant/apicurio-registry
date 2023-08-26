package io.apicurio.registry.rules.validity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ValidityList {

    private List<Validity> testCases;
}
