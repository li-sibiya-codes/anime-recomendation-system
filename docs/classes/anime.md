# Anime
The **Anime** class represents an individual anime within the Anime Recommendation System

It's one of two primary domain entities in the application, alongside **User**. An **Anime** object contains information that describes the anime itself, independant of ANY particular user's interaction with it.

The class provides the core anime data that can be stored, retrieved and evaluated by other components of the system, (**AnimeRepository**, **AnimeManager** and **RecommendationEngine**)

## Purpose
To provide a single representation of an anime that can be used throughout the application.

An **Anime** object represents properties that belong to the anime itself rather than a specific user.

For example, the fact that an anime is ongoing is a property of the anime. Whether a particular user has watched that anime, is currently watching it or has rated it is information about the relationship between that user and the anime, which is handled separately.

## Responsibilities 
- Representing an anime in the application.
- Storing the anime's relevant metadata.
- Providing access to the anime's properties through its methods.
- Allowing the anime's properties to be modified through setter methods.
- Representing the anime's release status using AnimeStatus.
- Validating certain values, such as ratings and episode counts.

## Attributes
The **Anime** class currently contains six attributes.

| **Attribute** | **Type** | **Description** |
|---|---|---|
| `animeId` | `int` | A unique identifier for the anime. |
| `title` | `String` | The title of the anime. |
| `genre` | `String` | The genre associated with the anime. |
| `status` | `AnimeStatus` | The current status of the anime. |
| `episodes` | `int` | The number of episodes associated with the anime. |
| `rating` | `double` | The anime's rating on a scale from 0 to 10. |

### `animeId`

`animeId` identifies an anime within the system.

It is not assigned through the constructor and therefore initially has Java's default integer value of `0`. It can be assigned later using `setAnimeId()`.

### `title`

`title` stores the name of the anime.

### `genre`

`genre` stores the genre information associated with the anime.

The current implementation represents the genre as a single `String`.

### `status`

`status` stores the anime's release status using the `AnimeStatus` enum.

Using an enum allows the application to represent the status using predefined values.

### `rating`

`rating` stores the anime's rating.

The current implementation uses a scale from `0` to `10`.

The class prevents ratings outside this range from being assigned.

## Constructor

The `Anime` class currently has one constructor:

java
public Anime(String title, String genre, AnimeStatus status, double rating, int episodes)

## Anime Status
The anime's release status is represented using the ***AnimeStatus*** enum rather than an arbitrary string.

This allows the application to represent a predefined set of possible anime statuses consistently.

Conceptually:

Anime
  │
  └── AnimeStatus
        ├── Completed
        ├── Ongoing
        └── Cancelled

The exact available statuses are determined by the ***AnimeStatus*** implementation. This is different from ***UserAnimeStatus***.

For example, an anime can be:

AnimeStatus → ONGOING
while a particular user could have:
UserAnimeStatus → WATCHING

The first describes the anime. The second describes the user's relationship with the anime.

## Relationship with UserAnime
**Anime** represents the anime itself, while **UserAnime** represents a specific user's relationship with that anime.

This distinction allows multiple users to interact with the same anime differently.

For example:

                    Anime
                  "One Piece"
                       │
             ┌─────────┴─────────┐
             │                   │
          User A              User B
             │                   │
         UserAnime           UserAnime
             │                   │
        Rating: 9/10         Rating: 6/10
        Watching             Completed

There is still only one underlying anime, but each user's interaction with it can be different.

## Relationship with AnimeRepository
**AnimeRepository** is responsible for obtaining and storing anime data, including reading anime information from the application's data source.

The repository creates or retrieves **Anime** objects and makes them available to other parts of the application.

Conceptually:

anime_data.txt
      │
      ▼
AnimeRepository
      │
      ▼
 Anime objects

Anime itself should not be responsible for reading files or managing the application's anime database.

## Relationship with AnimeManager
**AnimeManager** provides higher-level operations involving anime objects. Unlike **AnimeRepository** which is concerned primarily with accessing/storing anime data.

Conceptually:

AnimeManager
      │
      ▼
AnimeRepository
      │
      ▼
    Anime

The exact responsibilities of AnimeManager depend on its current implementation. For now it just stores Anime objects in the form of an arraylist

## Relationship with RecommendationEngine
**Anime** provides information that the **RecommendationEngine** can use when determining how suitable an anime is for a particular user.

The recommendation process can compare properties of an anime against information about the user.

Conceptually:

UserPreferences ────────┐
                        │
UserAnime ──────────────┤
                        ▼
                RecommendationEngine
                        ▲
                        │
Anime properties ───────┘

For example, if the recommendation system considers genres, an anime's genres can be compared with the genres preferred by the user.

If the recommendation system considers anime status, the anime's status can be compared with the user's status preferences.

The exact scoring factors and weighting are documented separately in *recommendation-system.md*.

## Data Lifecycle
Anime information can move through the system approximately as follows:

anime_data.txt
      │
      ▼
AnimeRepository
      │
      ▼
Anime objects
      │
      ├──────────────► AnimeManager
      │
      └──────────────► RecommendationEngine
                              │
                              ▼
                         Recommendations

The **Anime** class therefore acts as the common domain representation of an anime as it moves through different parts of the application.

## What Anime Does Not Do
The Anime class is not be responsible for:
- Reading anime data from files.
- Saving anime data to files.
- Managing users.
- Managing a user's watch status.
- Managing a user's rating of an anime.
- Calculating recommendation scores.
- Displaying information through the GUI.

Those responsibilities belong to other parts of the system. This separation helps keep the Anime class focused on representing the anime itself.

## Design Rationale
Keeping **Anime** as a separate domain entity makes it possible for the same anime to be used by multiple users without duplicating the underlying anime information.

It also separates anime-level information from user-specific information.

For example:
Anime
→ Title
→ Genres
→ Source
→ Anime Status

versus:

UserAnime
→ User
→ Anime
→ User's Status
→ User's Rating
→ Other user-specific information

This separation is important to the recommendation system because the engine can combine information about the anime with information about the user.

## Summary
Anime is the core representation of an anime in the Anime Recommendation system.

It stores information that describes the anime itself and provides that information to other components. **AnimeRepository** is responsible for obtaining anime data, **AnimeManager** handles higher-level anime operations, **UserAnime** represents a user's relationship with an anime and **RecommendationEngine** uses anime information together with user information to determine recommendations.

### Key Distinction:
**Anime represents what an anime is. UserAnime represents what a particular user has done with that anime**.