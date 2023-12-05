package com.jwtauthcruddemo.dtos.input;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Objects;

public record SignUpDto(String firstName,
                        String lastName,
                        String login,
                        char[] password) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final SignUpDto signUpDto = (SignUpDto) o;
        return Objects.equals(firstName, signUpDto.firstName)
                && Objects.equals(lastName, signUpDto.lastName)
                && Objects.equals(login, signUpDto.login)
                && Arrays.equals(password, signUpDto.password);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(firstName, lastName, login);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("login", login)
                .append("password", password)
                .toString();
    }
}
