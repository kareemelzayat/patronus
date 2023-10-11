# patronus

Requirements
=

- Admin user can create admin and non-admin users
- Admin user can create devices
- Admin user can assign devices to admin and non-admin users
- Admin user can retrieve all existing users and their assigned devices
- Admin user can retrieve all existing devices
- Non-admin user cannot do any of the above
- Non-admin user can only retrieve devices assigned to them

Domain Design (ERD)
=

## _User_

#### Database-oriented fields
* id: bigint
* version: integer
* date_created: timestamp
* last_updated: timestamp

#### Main fields
* first_name: varchar(255)
* last_name: varchar(255)
* birthday: datetime
* address: longtext

#### Credential fields
* email: varchar(255)
* password: varchar(255) (should be encrypted)
* role: varchar(30) (ADMIN or NON_ADMIN)

## _Device_

#### Database-oriented fields
* uuid: varchar(36)
* version: integer
* date_created: timestamp
* last_updated: timestamp

#### Main fields
* serial_number: varchar(16)
* model: varchar(255)
* phone: varchar(15)

UNIQUE KEY (serial_number, model)


## _User_Devices_

#### Mapping table for the One-to-Many relationship user-devices
* user_id: FK (User#id)
* device_uuid: FK (Device#uuid)

Endpoints
=

### Admin-only endpoints
- `POST /api/users` -> create new user { body: UserDto }
- `GET /api/users` -> retrieve a list of users (supports pagination params page,size)
- `GET /api/users/{id}` -> retrieve a user by id (supports pagination params page,size)
- `POST /api/devices` -> create new device { body: DeviceDto }
- `PUT /api/devices/{uuid}` -> assign a device to a user { body: AssignUserDto }

### Common endpoints
- `GET /api/devices` -> retrieve existing devices (non-admins retrieve only those assigned to them)
- `GET /api/devices/{uuid}` -> retrieve a device by uuid (non-admins can retrieve a device only if assigned to them)

## NOTES:

- All the endpoints require X-User-ID as a header for hypothetical user authentication

- I attempted to have the whole flow built by Kotlin Coroutines, but that was very challenging due to:
  - H2 database not being reactive -> was not able to use CoroutineCrudRepository
  - My inability to configure the netty server
- I tried to create an annotation `RequiresAdmin` with a `@Before` aspect that intercepts controller methods to check the user corresponding to the header X-User-Id if it has `ADMIN` role. If not, throws an exception. I was not successful in making that work though.




