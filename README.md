# ISS app
- MainActivity: Entry point to the app.
- LocationPermissionFragment: Shows a location permission UI.
- ISSDistanceFragment: Shows device location, ISS location and the distance between them. 
- AstronautsListFragment: Shows astronauts list.
- LocationProvider: Provides device location.
- BaseRepository: Makes web API and DB calls to provide various ISS info.
- LocationAndroidViewModel: Provides device and ISS location to the view.
- AstronautsViewModel: Provides astronauts data.
- ISSLocationHistoryViewModel: Provides ISS location history data
- AppDatabase: Stores iss positions in the local db.
- ISSPosition: iss_position db table schema.
- ISSPositionDao: iss_position data access interface.
