package com.example.journalApp.service;

import com.example.journalApp.entity.UserEntity;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(UserEntity.builder().userName("Shyam").password("123Shyam").build()), //UserEntity must have allArgsContr to use Builder, NoArgsCntr with Builder gives error(conflict)
                Arguments.arguments(UserEntity.builder().userName("Lila").password("123Lila").build())
        );
    }
}
