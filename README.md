# AATKit Android samples

Sample apps showing how to integrate [AATKit](https://aatkit.gitbook.io/android-integration) for Android.

All samples live in one Gradle project - every sample is a separate app module that can be built and run on its own.

## Samples

| Sample | Module | Description | Documentation |
|---|---|---|---|
| Sticky banner | `:banner:stickybanner` | Banner that stays in a fixed place on the screen and reloads automatically | [Sticky banner](https://aatkit.gitbook.io/android-integration/formats/banner/sticky-banner) |
| Infeed banner - infeed API | `:banner:infeedbannerusinginfeedapi` | Banners in a `RecyclerView` feed, each requested manually with `InfeedBannerPlacement` | [Infeed banner](https://aatkit.gitbook.io/android-integration/formats/banner/infeed-banner) |
| Infeed banner - banner cache | `:banner:infeedbannerusingbannercache` | Banners in a `RecyclerView` feed, served from a preloaded `BannerCache` | [Banner cache](https://aatkit.gitbook.io/android-integration/formats/banner/banner-cache) |
| Infeed banner - static API | `:banner:infeedbannersusingstaticapi` | One auto-reloading `StickyBannerPlacement` embedded in a `RecyclerView` feed | [Sticky banner](https://aatkit.gitbook.io/android-integration/formats/banner/sticky-banner) |
| Fullscreen | `:fullscreen:fullscreenusingautoreloading` | Fullscreen (interstitial) ad with automatic reloading | [Fullscreen](https://aatkit.gitbook.io/android-integration/formats/fullscreen-interstitial) |
| Start interstitial | `:fullscreen:startinterstitial` | Fullscreen ad shown after the splash screen | [Fullscreen](https://aatkit.gitbook.io/android-integration/formats/fullscreen-interstitial) |
| Rewarded video | `:fullscreen:rewardedvideousingautoreloading` | Rewarded video ad with automatic reloading | [Rewarded video](https://aatkit.gitbook.io/android-integration/formats/rewarded-video) |
| App open | `:appopen` | App open ad shown when the app comes to the foreground | [AppOpen](https://aatkit.gitbook.io/android-integration/formats/appopen-google) |
| Native ads | `:nativeads` | Native ads rendered inside a `RecyclerView` feed | [Native ad](https://aatkit.gitbook.io/android-integration/formats/native-ad) |
| Consent - managed | `:consent:managedconsentsample` | `ManagedConsent` with Google CMP - AATKit presents the consent dialog | [Consent](https://aatkit.gitbook.io/android-integration/start/consent) |
| Consent - vendor | `:consent:vendorconsentsample` | `VendorConsent` - the app decides about the consent for each non-IAB ad network | [Consent](https://aatkit.gitbook.io/android-integration/start/consent) |
| Consent - simple | `:consent:simpleconsentsample` | `SimpleConsent` - one consent value passed to all non-IAB ad networks | [Consent](https://aatkit.gitbook.io/android-integration/start/consent) |
| Consent - none | `:consent:noconsenthandlingsample` | AATKit initialised without any consent handling | [Consent](https://aatkit.gitbook.io/android-integration/start/consent) |

## Setup

1. Clone the repository:
   ```bash
   git clone git@github.com:AddApptr/aatkit-samples-android.git
   ```
2. Open the `aatkit-samples-android` folder in **Android Studio** (*File > Open*) and wait for the Gradle sync to finish.
3. Pick the sample in the run configurations drop-down (e.g. `aatkit-samples-android.banner.stickybanner`) and run it on a device or an emulator.

The samples can also be built from the command line:

```bash
./gradlew :banner:stickybanner:installDebug   # build and install a single sample
./gradlew assembleDebug                       # build all samples
```

Requirements: a recent Android Studio (the project uses Android Gradle Plugin 9) and Android SDK Platform 36.

The samples run in AATKit test mode (`setTestModeAccountId`), so they show test ads out of the box. Remember to remove the test mode before publishing your own app.

## Project structure

- `settings.gradle` - list of the sample modules and the Maven repositories of AATKit and the ad networks
- `gradle/libs.versions.toml` - versions of all dependencies, shared by every sample
- `build.gradle` - configuration common to all samples
- `<group>/<sample>/build.gradle` - build file of a single sample

### Updating AATKit

The AATKit version is declared in one place - change `aatkit` in [`gradle/libs.versions.toml`](gradle/libs.versions.toml) and all samples will use it:

```toml
[versions]
aatkit = "3.18.3"
```
