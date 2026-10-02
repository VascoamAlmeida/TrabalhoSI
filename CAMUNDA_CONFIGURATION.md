# Camunda configuration

The Java workers connect to Camunda 8 / Zeebe using environment variables. Real credentials are intentionally not stored in this repository.

Required variables:

- `CAMUNDA_ZEEBE_ADDRESS`
- `CAMUNDA_ZEEBE_CLIENT_ID`
- `CAMUNDA_ZEEBE_CLIENT_SECRET`

The project uses the standard Camunda Cloud OAuth endpoint and the `zeebe.camunda.io` audience.

## Example

An example file is provided as `.env.example`. It is documentation only; the Java applications read the variables from the operating-system environment.

On PowerShell, for example:

```powershell
$env:CAMUNDA_ZEEBE_ADDRESS="your-zeebe-address"
$env:CAMUNDA_ZEEBE_CLIENT_ID="your-client-id"
$env:CAMUNDA_ZEEBE_CLIENT_SECRET="your-client-secret"
```

Do not commit real credentials. If credentials were previously committed, submitted, shared, or stored in an archive, revoke/rotate them in Camunda before reusing the project.
