package io.github.dylmeadows.eontimer.service

import io.github.dylmeadows.eontimer.model.GitHubRelease
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class GitHubService @Autowired constructor(
    private val webClient: WebClient) {

    fun getLatestRelease(): Mono<GitHubRelease> {
        return webClient.get()
            .uri("/repos/dylmeadows/EonTimer/releases/latest")
            .retrieve().bodyToMono(GitHubRelease::class.java)
    }
}