package info.octera.droidstorybox.presentation

import android.net.Uri
import androidx.core.net.toUri
import info.octera.droidstorybox.domain.model.PackSource
import info.octera.droidstorybox.domain.model.RemotePack
import info.octera.droidstorybox.domain.model.RemoteThumbs
import info.octera.droidstorybox.domain.model.pack.ControlSettings
import info.octera.droidstorybox.domain.model.pack.Pack
import info.octera.droidstorybox.domain.model.pack.PackMetadata
import info.octera.droidstorybox.domain.model.pack.Stage
import info.octera.droidstorybox.domain.model.pack.StageType
import java.io.File
import java.util.UUID

object PreviewFakeData {
    val stage = Stage(
        uuid = UUID.randomUUID(),
        type = StageType.STAGE,
        name = "foo",
        image = null,
        audio = Uri.parse("foo"),
        okTransition = null,
        homeTransition = null,
        controlSettings = ControlSettings(false,false,false,false,false),
        squareOne = true

    )
    val pack= Pack(
        metadata = PackMetadata(
            format = "foo",
            title = "Pack 1",
            version = 4,
            description = "Foo bar foo bar",
            age = 4,
            thumbsnail = ByteArray(0),
            nightModeAvailable = false,
            uri = File("").toUri(),
        ),
        actions = emptyList(),
        stages = emptyList()
    )
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
            thumbs = RemoteThumbs(
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
            thumbs = RemoteThumbs(
                "https://v5.airtableusercontent.com/v3/u/31/31/1722463200000/07e1jn_u2G6hju-TL8DZEA/uzGY57fxEBgvAtFzf2J01v0W-Or7JxNiwbHxddoKE54oOHPIOCdnGfqkSBONfgnr_4f2-ltC8pLYdXNRFlMJ762JUcfrFLz1H2j1zvzvynOY8CZRjuB5udG_4WT15KnfOS4XgCRH-e7Fd8OQnT_YUg/6w04S_wxzUPUk7tO5xSEAN3MRlZKIyp5RvrnmkOUkNU",
                "https://v5.airtableusercontent.com/v3/u/31/31/1722463200000/4Uf2sUMdIIGn9vsBLeAznQ/5LPeMOKGWIVvorwNRig0T0LvUBQoVsx3BbUg1PnvO8nW8KBqMRWysHYeN_3b21k3-s91VqoX2Qdk-MuHGhk2BJEvnYI-_s7iMg9xJR3rqRU_CEuMMUSZkdvMm0gzOH4yMiMlBfxC2rsYPENEv2K9Hw/E4_T_84JwphK7YoLkqCB6QIzWJwX5uzAPzLbEZg6Pzw"
            )
        ),
    )

    val localPacks = listOf(
        PackMetadata(
            format = "foo",
            title = "Pack 1",
            version = 4,
            description = "Foo bar foo bar",
            age = 4,
            thumbsnail = ByteArray(0),
            nightModeAvailable = false,
            uri = File("").toUri(),
        ),
        PackMetadata(
            format = "foo",
            title = "Pack 2",
            version = 4,
            description = "Foo bar foo bar",
            age = 4,
            thumbsnail = ByteArray(0),
            nightModeAvailable = false,
            uri = File("").toUri(),
        ),
    )
}