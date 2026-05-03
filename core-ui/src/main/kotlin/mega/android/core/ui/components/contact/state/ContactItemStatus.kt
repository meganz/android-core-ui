package mega.privacy.android.feature.contact.components

/**
 * Presentational status enum decoupled from any domain type.
 * [Unknown] hides the inline status indicator entirely.
 */
enum class ContactItemStatus {
    /**
     * Online
     */
    Online,

    /**
     * Away
     */
    Away,

    /**
     * Busy
     */
    Busy,

    /**
     * Offline
     */
    Offline,

    /**
     * Unknown
     */
    Unknown,
}