package info.octera.droidstorybox.presentation

import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.model.Thumbs
import java.time.ZoneId
import java.time.ZonedDateTime

object PreviewFakeData {
    val packSources = listOf(
        PackSource(name = "Source1", url = "https://foo"),
        PackSource(name = "Source1", url = "https://foo"),
        )
    val remotePacks = listOf(
        RemotePack(
            4,
            "Pack 1",
            "Foo bar foo bar",
            "http://foo",
            awards = listOf(),
            createdAt = "2023-12-02T12:13:31.000Z",
            updatedAt = "2023-12-02T12:13:31.000Z",
            thumbs = Thumbs(
                "https://v5.airtableusercontent.com/v3/u/31/31/1722463200000/07e1jn_u2G6hju-TL8DZEA/uzGY57fxEBgvAtFzf2J01v0W-Or7JxNiwbHxddoKE54oOHPIOCdnGfqkSBONfgnr_4f2-ltC8pLYdXNRFlMJ762JUcfrFLz1H2j1zvzvynOY8CZRjuB5udG_4WT15KnfOS4XgCRH-e7Fd8OQnT_YUg/6w04S_wxzUPUk7tO5xSEAN3MRlZKIyp5RvrnmkOUkNU",
                "https://v5.airtableusercontent.com/v3/u/31/31/1722463200000/4Uf2sUMdIIGn9vsBLeAznQ/5LPeMOKGWIVvorwNRig0T0LvUBQoVsx3BbUg1PnvO8nW8KBqMRWysHYeN_3b21k3-s91VqoX2Qdk-MuHGhk2BJEvnYI-_s7iMg9xJR3rqRU_CEuMMUSZkdvMm0gzOH4yMiMlBfxC2rsYPENEv2K9Hw/E4_T_84JwphK7YoLkqCB6QIzWJwX5uzAPzLbEZg6Pzw"
            )
        ),
        RemotePack(
            5,
            "Pack 2",
            "Foo bar foo bar",
            "http://foo",
            awards = listOf(),
            createdAt = "2023-12-02T12:13:31.000Z",
            updatedAt = "2023-12-02T12:13:31.000Z",
            thumbs = Thumbs(
                "https://v5.airtableusercontent.com/v3/u/31/31/1722463200000/07e1jn_u2G6hju-TL8DZEA/uzGY57fxEBgvAtFzf2J01v0W-Or7JxNiwbHxddoKE54oOHPIOCdnGfqkSBONfgnr_4f2-ltC8pLYdXNRFlMJ762JUcfrFLz1H2j1zvzvynOY8CZRjuB5udG_4WT15KnfOS4XgCRH-e7Fd8OQnT_YUg/6w04S_wxzUPUk7tO5xSEAN3MRlZKIyp5RvrnmkOUkNU",
                "https://v5.airtableusercontent.com/v3/u/31/31/1722463200000/4Uf2sUMdIIGn9vsBLeAznQ/5LPeMOKGWIVvorwNRig0T0LvUBQoVsx3BbUg1PnvO8nW8KBqMRWysHYeN_3b21k3-s91VqoX2Qdk-MuHGhk2BJEvnYI-_s7iMg9xJR3rqRU_CEuMMUSZkdvMm0gzOH4yMiMlBfxC2rsYPENEv2K9Hw/E4_T_84JwphK7YoLkqCB6QIzWJwX5uzAPzLbEZg6Pzw"
            )
        ),    )
}