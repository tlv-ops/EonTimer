package io.github.dylmeadows.eontimer.model

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
data class GitHubRelease(
    @JsonProperty("tag_name")
    var tagName: String?,
    @JsonProperty("html_url")
    var htmlUrl: String?)