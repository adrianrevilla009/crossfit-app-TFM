# JMeter Helm Chart

## Introduction

This chart bootstraps JMeter Cluster on Kubernetes using the Helm package manager.

## Prerequisites

- Helm cli with Kubernetes cluster configured.
- Use Kubernetes version v1.19 and later for best experience.

## Configure JMeter Helm repo

```bash
helm repo add jmeter https://liukunup.github.io/helm-charts
```

### Installing the Chart

Install this chart using:

```bash
helm install --namespace jmeter --generate-name jmeter/jmeter
```

The command deploys JMeter on the Kubernetes cluster in the default configuration.

## Uninstalling the Chart

Assuming your release is named as `my-release`, delete it using the command:

```bash
helm delete my-release
```

or

```bash
helm uninstall my-release
```

The command removes all the Kubernetes components associated with the chart and deletes the release.