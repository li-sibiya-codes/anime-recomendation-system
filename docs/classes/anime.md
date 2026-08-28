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
- Maintaining the anime's own status and other intrinsic information.
- Providing anime information to other parts of the application.
- Acting as an input to the recommendation system when the anime's properties are evaluated against a user's preferences.

## Anime Information
An **Anime** object may contain information used to describe and identify the anime.

The exact fields should correspond to the current implementation of the class. Depending on the current version of the system, this can include information such as:

|**Information** | **Description**                                    |
|-----------------|---------------------------------------------------|
|Title	          |The name of the anime                              |
|Genre	          |The genre associated with the anime                |
|Status	          |The release/completion status of the anime         |
|Source	          |The original source material or format of the anime|
|Rating	          |Rating information associated with the anime       |
|ID	              |A unique identifier for the anime                  |

These properties describe the anime itself and are therefore distinct from information stored in UserAnime.